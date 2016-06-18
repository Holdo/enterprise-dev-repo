sap.ui.define([
    'jquery.sap.global',
    'Repository/BaseController',
    'sap/ui/core/Fragment',
    'sap/ui/model/Filter',
    'sap/ui/model/json/JSONModel',
    "sap/m/MessageToast",
    "sap/m/MessageBox",
    "sap/m/Link"
], function (jQuery, BaseController, Fragment, Filter, JSONModel, MessageToast, MessageBox, Link) {
    "use strict";

    var BrowseController = BaseController.extend("Repository.browse.Browse", {
        onInit: function () {
            var that = this;
            this.toBeLink = null;

            this.refreshList("");
        },
        onExit: function () {
            //Pls do not leave
        },
        refreshList: function (sFullPath) {
            var that = this;
            var ws = new WebSocket("ws://" + document.location.host + "/websocket/command/listDir");
            ws.onopen = function () {
                var sMessage = "{\"namespace\" : \"" + sFullPath + "\"}";
                sMessage = sMessage.replace(/\\/g, "\\\\");
                console.log("Sending " + sMessage);
                ws.send(sMessage);
            };
            ws.onmessage = function (oEvent) {
                console.log("Received: " + oEvent.data);
                var oModel = new JSONModel(JSON.parse(oEvent.data));
                that.getView().setModel(oModel);
            };
        },
        handleListItemPress: function (oEvent) {
            var that = this;
            var oListItem = oEvent.getSource();
            var sClickedTitle = oListItem.getTitle();
            var sClickedPath = oListItem.data("path");
            console.log(sClickedTitle);
            if (oListItem.getType() == "Navigation") {
                var oBreadcrumbs = that.getView().byId("breadcrumbs");
                var ws = new WebSocket("ws://" + document.location.host + "/websocket/command/listDir");
                ws.onopen = function () {
                    var sMessage = "{\"namespace\" : \"" + sClickedPath + "\\" + sClickedTitle + "\"}";
                    console.log("Sending " + sMessage.replace(/\\/g, "\\\\"));
                    ws.send(sMessage.replace(/\\/g, "\\\\"));
                };
                ws.onmessage = function (oEvent) {
                    console.log("Received: " + oEvent.data);
                    that.getView().setModel(new JSONModel(JSON.parse(oEvent.data)));
                    if (oBreadcrumbs.getCurrentLocationText() == "Root") {
                        oBreadcrumbs.addLink(new Link({
                            text: "Root",
                            emphasized: true
                        }).attachPress(function () {
                            that.handleLinkPress(this, "", "");
                        }));
                    } else {
                        oBreadcrumbs.addLink(that.toBeLink);
                    }
                    oBreadcrumbs.setCurrentLocationText(sClickedTitle);
                    that.toBeLink = new Link({
                        text: sClickedTitle
                    }).attachPress(function () {
                        console.log("Pressed link with path " + sClickedPath);
                        that.handleLinkPress(this, sClickedPath, sClickedTitle);
                    });
                };
            } else {
                console.log("Clicked on a " + sClickedPath);
            }
        },
        handleLinkPress: function (oSource, sPath, sName) {
            var that = this;
            var oBreadcrumbs = this.getView().byId("breadcrumbs");
            if (oSource.getText() == "Root") {
                oBreadcrumbs.removeAllLinks();
                oBreadcrumbs.setCurrentLocationText("Root");
                this.refreshList("");
                return;
            }
            oBreadcrumbs.setCurrentLocationText(sName);
            var iLinkIndex = oBreadcrumbs.indexOfLink(oSource);
            var i = oBreadcrumbs.getLinks().length - 1;
            while (i != iLinkIndex) {
                oBreadcrumbs.removeLink(i);
                i--;
            }
            oBreadcrumbs.removeLink(iLinkIndex);
            this.toBeLink = new Link({
                text: sName
            }).attachPress(function () {
                console.log("Pressed link with path " + sPath);
                that.handleLinkPress(this, sPath, sName);
            });
            this.refreshList(sPath + "\\" + sName);
        }
    });

    return BrowseController;
}, /* bExport= */ true);
function replaceAll(str, find, replace) {
    return str.replace(new RegExp(find, 'g'), replace);
}
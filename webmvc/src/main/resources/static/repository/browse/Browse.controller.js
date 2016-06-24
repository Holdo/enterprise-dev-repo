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
            this.getRouter().getRoute("browse").attachPatternMatched(this._onObjectMatched, this);
            this.toBeLink = null;
        },
        onExit: function () {
            //Pls do not leave
        },
        _onObjectMatched: function (oEvent) {
            var sURLPath = decodeURIComponent(oEvent.getParameter("arguments").fullPath);
            if (sURLPath == "") {
                console.error("ERROR: Matched empty path");
                return;
            }
            var oBreadcrumbs = this.getView().byId("breadcrumbs");
            if (sURLPath == "root") {
                if (oBreadcrumbs.getLinks().length > 0) {
                    oBreadcrumbs.removeAllLinks();
                    oBreadcrumbs.setCurrentLocationText("root");
                }
                this.refreshList("");
            } else {
                sURLPath = sURLPath.checkPath();
                var that = this;
                console.log("Matched path " + sURLPath);
                if (oBreadcrumbs.getLinks().length == 0) {
                    var aPathSplit = sURLPath.split("/");
                    //Create root link
                    oBreadcrumbs.addLink(new Link({
                        text: "root"
                    }).attachPress(function () {
                        that.handleLinkPress(this, "", "");
                    }));
                    if (aPathSplit.length == 1) {
                        oBreadcrumbs.setCurrentLocationText(aPathSplit[0]);
                    } else {
                        for (var i = 0; i < aPathSplit.length - 1; i++) {
                            oBreadcrumbs.addLink(new Link({
                                text: aPathSplit[i]
                            }).attachPress(function () {
                                console.log("Pressed link with path " + this.getText());
                                that.handleLinkPress(this, sClickedPath, sClickedTitle);
                            }));
                        }
                        oBreadcrumbs.setCurrentLocationText(aPathSplit[aPathSplit.length - 1]);
                    }
                }
                this.refreshList(sURLPath);
            }
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
            var sClickedFullPath = oListItem.data("fullPath");
            var sClickedVersion = oListItem.data("version");
            var ws;
            if (oListItem.getType() == "Navigation") { //is folder
                var oBreadcrumbs = that.getView().byId("breadcrumbs");
                ws = new WebSocket("ws://" + document.location.host + "/websocket/command/listDir");
                ws.onopen = function () {
                    var oMessage = {
                        namespace : sClickedFullPath.replace(/\\/g, "\\\\")
                    };
                    var sMessage = JSON.stringify(oMessage);
                    console.log("Sending " + sMessage);
                    ws.send(sMessage);
                };
                ws.onmessage = function (oEvent) {
                    console.log("Received: " + oEvent.data);
                    that.getView().setModel(new JSONModel(JSON.parse(oEvent.data)));
                    if (oBreadcrumbs.getCurrentLocationText() == "root") {
                        oBreadcrumbs.addLink(new Link({
                            text: "root",
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
                    that.getRouter().navTo("browse", {
                        fullPath : encodeURIComponent(sClickedFullPath.checkPath())
                    });
                };
            } else { //is file
                this.getRouter().navTo("artifact", {
                    fullPath : encodeURIComponent(sClickedFullPath.checkPath())
                });
            }
        },
        handleLinkPress: function (oSource, sPath, sName) {
            var that = this;
            var oBreadcrumbs = this.getView().byId("breadcrumbs");
            if (oSource.getText() == "root") {
                that.getRouter().navTo("browse", {
                    fullPath : "root"
                });
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
            that.getRouter().navTo("browse", {
                fullPath : encodeURIComponent((sPath == "")? sName : sPath + "/" + sName)
            });
        }
    });

    return BrowseController;
}, /* bExport= */ true);
if (!String.prototype.checkPath) {
    String.prototype.checkPath = function() {
        var i = 0;
        while (this.charAt(i) == '\\' || this.charAt(i) == "/") i++;
        return this.slice(i).replace(/\\/g, "/");
    };
}
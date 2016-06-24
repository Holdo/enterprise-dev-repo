sap.ui.define([
    'jquery.sap.global',
    'sap/m/MessageToast',
    'Repository/BaseController',
    'sap/ui/model/json/JSONModel',
    "sap/m/MessageBox"
], function(jQuery, MessageToast, BaseController, JSONModel, MessageBox) {
    "use strict";

    var AppController =  BaseController.extend("Repository.App", {
        onInit : function () {
            //Create App Context
            sap.ui.getCore().AppContext = {};
            //Attach tab keyup to metaTypeSelector
            this.byId("metaTypeSelector").attachBrowserEvent("tab keyup", function(oEvent){
                this._bKeyboard = oEvent.type == "keyup";
            }, this);
        },
        onExit : function () {
            if (this._searchTypeMenu) this._searchTypeMenu.destroy();
            if (this._searchOverlay) this._searchOverlay.destroy();
        },
        onAfterRendering : function () {
            $("body").fadeIn("slow");
        },
        handleLogoffPress : function () {
            //window.location.href = "/secured/logout";
        },
        handlePressHome : function () {
            this.onNavHome();
        },
        handlePressBack : function (oEvent) {
            this.onNavBack();
        },
        handleUserItemPressed: function(oEvent) {
            MessageToast.show("User Button Pressed");
        },
        handleSearchItemSelect: function(oEvent) {
            var that = this;
            this._searchOverlay.close();
            var oSelectedListItem = oEvent.getSource();
            var ws = new WebSocket("ws://" + document.location.host + "/websocket/command/getArtifactMetadata");
            ws.onopen = function () {
                var oMessage = {
                    fullPath : oSelectedListItem.data("fullPath").replace(/\\/g, "\\\\"),
                    version : parseInt(oSelectedListItem.data("version"))
                };
                var sMessage = JSON.stringify(oMessage);
                console.log("Sending " + sMessage);
                ws.send(sMessage);
            };
            ws.onmessage = function (oEvent) {
                console.log("Received: " + oEvent.data);
                that.getRouter().navTo("artifact", {
                    fullPath : encodeURIComponent(oSelectedListItem.data("fullPath").checkPath())
                });
            };
        },
        handleShellOverlayClosed: function() {
            //MessageToast.show("Overlay closed");
        },
        handlePressConfiguration: function(oEvent) {
            var oItem = oEvent.getSource();
            var oShell = this.getView().byId("myShell");
            var bState = oShell.getShowPane();
            oShell.setShowPane(!bState);
            oItem.setShowMarker(!bState);
            oItem.setSelected(!bState);
        },
        handleSearchPressed: function(oEvent) {
            var oMetaTypeSelector = this.getView().byId("metaTypeSelector");
            if (oMetaTypeSelector.getText() == "Select search type") {
                oMetaTypeSelector.setType("Reject");
                MessageBox.error(
                    "Please select the search type to search.",
                    {
                        title: "Information",
                        actions: [MessageBox.Action.OK]
                    }
                );
                return;
            }

            var sQuery = oEvent.getParameter("query");
            if(sQuery == "") return;

            // create Overlay only once
            if (!this._searchOverlay) {
                this._searchOverlay = sap.ui.xmlfragment("repository.ShellOverlay", this);
                this.getView().addDependent(this._searchOverlay);
            }
            
            //load data
            var that = this;
            var ws = new WebSocket("ws://" + document.location.host + "/websocket/command/search");
            ws.onopen = function () {
                var oMessage = {
                    fileType : that.fileType.trim().replace(/\s+/g, ''),
                    metaParameterType : that.metaParameterType.trim().replace(/\s+/g, ''),
                    parameterName : sQuery
                };
                //sMessage = sMessage.replace(/\\/g, "\\\\");
                var sMessage = JSON.stringify(oMessage);
                console.log("Sending " + sMessage);
                ws.send(sMessage);
            };
            ws.onmessage = function (oEvent) {
                console.log("Received: " + oEvent.data);
                var oData = {
                    searchFieldContent: sQuery,
                    fileType : that.fileType,
                    metaParameterType : that.metaParameterType,
                    resultData: JSON.parse(oEvent.data)
                };
                that._searchOverlay.setModel(new JSONModel(oData));
                that._searchOverlay.open();
            };
        },
        handlePressMetaTypeSelector: function (oEvent) {
            var oMetaTypeSelector = oEvent.getSource();

            // create menu only once
            if (!this._searchTypeMenu) {
                this._searchTypeMenu = sap.ui.xmlfragment("repository.SearchTypeMenu", this);
                this.getView().addDependent(this._searchTypeMenu);
            }

            var eDock = sap.ui.core.Popup.Dock;
            this._searchTypeMenu.open(this._bKeyboard, oMetaTypeSelector, eDock.BeginTop, eDock.BeginBottom, oMetaTypeSelector);
        },
        handleSearchTypeMenuItemPress: function (oEvent) {
            if (oEvent.getParameter("item").getSubmenu()) return;
            var oMetaTypeSelector = this.getView().byId("metaTypeSelector");
            var oItemPressed = oEvent.getParameter("item");

            this.metaParameterType = oItemPressed.getText();
            this.fileType = oItemPressed.getParent().getParent().getText();
            oMetaTypeSelector.setText(this.fileType + " " + this.metaParameterType);
            oMetaTypeSelector.setType("Accept");
        }
    });
    return AppController;
});
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for(var i=0; i<ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0)==' ') c = c.substring(1);
        if (c.indexOf(name) == 0) return c.substring(name.length,c.length);
    }
    return "";
}
if (!String.prototype.checkPath) {
    String.prototype.checkPath = function() {
        var i = 0;
        while (this.charAt(i) == '\\' || this.charAt(i) == "/") i++;
        return this.slice(i).replace(/\\/g, "/");
    };
}
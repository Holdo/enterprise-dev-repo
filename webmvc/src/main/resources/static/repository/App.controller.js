sap.ui.define([
    'jquery.sap.global',
    'sap/m/MessageToast',
    'sap/ui/core/Fragment',
    'Repository/BaseController',
    'sap/ui/model/json/JSONModel'
], function(jQuery, MessageToast, Fragment, BaseController, JSONModel) {
    "use strict";

    var AppController =  BaseController.extend("Repository.App", {
        onInit : function () {
            sap.ui.getCore().AppContext = {};
        },
        onExit : function () {
            if (this._oPopover) this._oPopover.destroy();
            if (this._overlay) this._overlay.destroy();
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
            MessageToast.show("Search Entry Selected: " + oEvent.getSource().getTitle());
        },
        handleShellOverlayClosed: function() {
            MessageToast.show("Overlay closed");
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
            var that = this;
            var sQuery = oEvent.getParameter("query");
            if(sQuery == "") return;

            // create Overlay only once
            if (!this._overlay) {
                this._overlay = sap.ui.xmlfragment("repository.ShellOverlay", this);
                this.getView().addDependent(this._overlay);
            }

            // mock data
            var aResultData = [];
            for(var i = 0; i < 25; i++) {
                aResultData.push({
                    title:(i + 1) + ". " + sQuery
                });
            }
            console.log(sQuery);
            var oData = {
                array: [{lul: "first"}, {lul: "second"}],
                searchFieldContent: sQuery,
                resultData: aResultData
            };
            this._overlay.setModel(new JSONModel(oData));
            this._overlay.getSearch().getItems()[0].setValue(sQuery);

            this._metaTypeSelector = this._overlay.getSearch().getItems()[1];
            this._metaTypeSelector.onclick = function() {
                console.log("Clicked on:");
                console.log(that._metaTypeSelector);
            };

            // set reference to shell and open overlay
            this._overlay.open();
        },
        onMetaTypeSelectChange: function (oEvent) {
            console.log(oEvent.getSource());
            console.log(oEvent.getSource().getSelectedKey());
            if (!this._oPopover) {
                this._oPopover = sap.ui.xmlfragment("repository.SearchTypePopover", this);
                //this._oPopover.bindElement("/ProductCollection/0"); //bind context
                this.getView().addDependent(this._oPopover);
            }
            this._oPopover.openBy(oEvent.getSource());
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
sap.ui.define([
    'jquery.sap.global',
    'sap/m/MessageToast',
    'sap/ui/core/Fragment',
    'sap/ui/core/mvc/Controller',
    'sap/ui/model/json/JSONModel'
], function(jQuery, MessageToast, Fragment, Controller, JSONModel) {
    "use strict";

    var AppController =  Controller.extend("repository.App", {
        onInit : function () {
            sap.ui.getCore().AppContext = {};
        },
        handleLogoffPress : function () {
            //window.location.href = "/secured/logout";
        },
        handlePressHome : function () {
            window.location.href = "/";
        },
        handlePressBack : function (oEvent) {
            oEvent.getSource().setVisible(false);
            this.getOwnerComponent().getTargets().display("mainView");
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
            var sQuery = oEvent.getParameter("query");
            if(sQuery == "") return;

            // create Overlay only once
            if (!this._overlay) {
                this._overlay = sap.ui.xmlfragment("repository.ShellOverlay", this);
                this.getView().addDependent(this._overlay);
            }

            // mock data
            var aResultData = [];
            for(var i = 0; i < 10; i++) {
                aResultData.push({
                    title:(i + 1) + ". " + sQuery,
                    text:"Lorem ipsum sit dolem"
                });
            }
            var oData = {
                searchFieldContent: sQuery,
                resultData: aResultData
            };
            var oModel = new JSONModel();
            oModel.setData(oData);
            this._overlay.setModel(oModel);

            // set reference to shell and open overlay
            this._overlay.setShell(this.getView().byId("myShell"));
            this._overlay.open();
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
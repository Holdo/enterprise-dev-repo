sap.ui.define([
    'jquery.sap.global',
    'sap/ui/core/mvc/Controller',
    'sap/ui/model/json/JSONModel',
    'sap/m/MessageToast'
], function(jQuery, Controller, JSONModel, MessageToast) {
    "use strict";

    var LaunchpadController = Controller.extend("repository.Launchpad", {
        onInit: function(oEvent) {
            //Set JSON model
            var tileCollectionJSON= {
                "TileCollection" : [
                    {
                        "icon" : "sap-icon://search",
                        "type" : "Monitor",
                        "title" : "Search"
                    },
                    {
                        "icon" : "sap-icon://upload-to-cloud",
                        "type" : "Monitor",
                        "title" : "Upload"
                    },
                    {
                        "icon" : "sap-icon://sys-monitor",
                        "type" : "Monitor",
                        "title" : "Administer"
                    }
                ]
            };
            this.tileModelJSON = new JSONModel();
            this.tileModelJSON.setData({data: tileCollectionJSON});
            this.getView().setModel(this.tileModelJSON);
        },
        onExit : function (oEvent) {
            //Pls do not leave
        },
        handleTilePress : function (oEvent) {
            switch(oEvent.oSource.getTitle()) {
                case "Search":
                    MessageToast.show("Search Tile Pressed");
                    break;
                case "Upload":
                    MessageToast.show("Upload Tile Pressed");
                    break;
                case "Administer":
                    MessageToast.show("Administer Tile Pressed");
                    break;
                default:
                    break;
            }
        }
    });

    return LaunchpadController;
})
;
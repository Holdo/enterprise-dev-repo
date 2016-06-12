sap.ui.define([
    'jquery.sap.global',
    'Repository/BaseController',
    'sap/ui/model/json/JSONModel',
    'sap/m/MessageToast'
], function(jQuery, BaseController, JSONModel, MessageToast) {
    "use strict";

    var LaunchpadController = BaseController.extend("Repository.Launchpad", {
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
                    this.getRouter().navTo("upload");
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
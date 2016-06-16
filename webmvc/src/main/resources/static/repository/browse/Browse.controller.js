sap.ui.define([
    'jquery.sap.global',
    'Repository/BaseController',
    'sap/ui/core/Fragment',
    'sap/ui/model/Filter',
    'sap/ui/model/json/JSONModel',
    "sap/m/MessageToast",
    "sap/m/MessageBox"
], function (jQuery, BaseController, Fragment, Filter, JSONModel, MessageToast, MessageBox) {
    "use strict";

    var UploadController = BaseController.extend("Repository.browse.Browse", {
        onInit: function () {
            //Set model
            this.getView().setModel(new JSONModel({
                test: "",
                test2: ""
            }));
        },
        onExit: function () {
            //Pls do not leave
        }
    });

    return UploadController;
}, /* bExport= */ true);

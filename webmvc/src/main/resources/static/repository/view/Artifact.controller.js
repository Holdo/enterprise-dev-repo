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

    var AppContext = sap.ui.getCore().AppContext;
    var ArtifactController = BaseController.extend("Repository.view.Artifact", {
        onInit: function () {
            var oData = {
                artifactName : "Test.xsd",
                artifactLocation : "namespace",
                versions : ["1", "2"]
            };
            this.getView().setModel(AppContext.oArtifactModel);
        },
        onExit: function () {
            //Pls do not leave
        }
    });

    return ArtifactController;
}, /* bExport= */ true);
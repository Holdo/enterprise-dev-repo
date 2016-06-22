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

    var AppContext = sap.ui.getCore().AppContext;
    var ArtifactController = BaseController.extend("Repository.view.Artifact", {
        onInit: function () {
            this._oTypesGrid = this.getView().byId("typesGrid");
            var oData = {
                artifactName : "Test.xsd",
                artifactPath : "namespace",
                versions : ["1", "2"]
            };

            this.refreshView();

            AppContext.oArtifactController = this;
        },
        onExit: function () {
            //Pls do not leave
        },
        refreshView: function () {
            this._oTypesGrid.destroyContent();
            this.getView().setModel(AppContext.oArtifactModel);

            if (AppContext.oArtifactModel.getProperty("/attributes")) this._oTypesGrid.addContent(this.createTypeBox("attribute"));
            if (AppContext.oArtifactModel.getProperty("/elements")) this._oTypesGrid.addContent(this.createTypeBox("element"));
            if (AppContext.oArtifactModel.getProperty("/simpleTypes")) this._oTypesGrid.addContent(this.createTypeBox("simpleType"));
            if (AppContext.oArtifactModel.getProperty("/complexTypes")) this._oTypesGrid.addContent(this.createTypeBox("complexType"));
            if (AppContext.oArtifactModel.getProperty("/filters")) this._oTypesGrid.addContent(this.createTypeBox("filter"));
            if (AppContext.oArtifactModel.getProperty("/listeners")) this._oTypesGrid.addContent(this.createTypeBox("listener"));
            if (AppContext.oArtifactModel.getProperty("/operations")) this._oTypesGrid.addContent(this.createTypeBox("operation"));
            if (AppContext.oArtifactModel.getProperty("/requests")) this._oTypesGrid.addContent(this.createTypeBox("request"));
            if (AppContext.oArtifactModel.getProperty("/responses")) this._oTypesGrid.addContent(this.createTypeBox("response"));
        },
        createTypeBox: function (sTypeName) {
            var oTypeBox = new sap.m.VBox(sTypeName + "TypeBox");
            var oPanel = new sap.m.Panel();
            var oCenteringBox = new sap.m.HBox({justifyContent : "Center"});
            var oText = new sap.m.Text({text : sTypeName.capitalize() + "s"}).addStyleClass("sapMH5FontSize");
            var oList = new sap.m.List({growing : "true", growingThreshold : 3}).bindAggregation(
                "items", "/" + sTypeName + "s",
                new sap.m.StandardListItem({type : "Inactive", title : "{" + sTypeName + "}"}),
                new sap.ui.model.Sorter(sTypeName, false));
            oCenteringBox.addItem(oText);
            oPanel.addContent(oCenteringBox);
            oTypeBox.addItem(oPanel);
            oTypeBox.addItem(oList);

            return oTypeBox;
        }
    });

    return ArtifactController;
}, /* bExport= */ true);
String.prototype.capitalize = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
};
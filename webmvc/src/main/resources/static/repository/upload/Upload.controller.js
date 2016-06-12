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

    var UploadController = BaseController.extend("Repository.upload.Upload", {
        onInit: function () {
            //Set model
            this.getView().setModel(new JSONModel({
                projectName: "",
                fileName: ""
            }));

            this.file = null;

            // attach handlers for validation errors
            sap.ui.getCore().attachValidationError(function (evt) {
                var control = evt.getParameter("element");
                if (control && control.setValueState) {
                    control.setValueState("Error");
                }
            });
            sap.ui.getCore().attachValidationSuccess(function (evt) {
                var control = evt.getParameter("element");
                if (control && control.setValueState) {
                    control.setValueState("Success");
                }
            });
        },
        onExit: function () {
            //Pls do not leave
        },
        handleFileTypeMissmatch: function (oEvent) {
            var aFileTypes = oEvent.getSource().getFileType();
            jQuery.each(aFileTypes, function (key, value) {
                aFileTypes[key] = "*." + value
            });
            this.getView().byId("artifactFileUploader").setValueState("Error");
            var sSupportedFileTypes = aFileTypes.join(", ");
            MessageToast.show("The file type *." + oEvent.getParameter("fileType") +
                " is not supported. Choose one of the following types: " + sSupportedFileTypes);
        },
        handleFileUploaderChange: function (oEvent) {
            var file = oEvent.mParameters.files[0];
            if (file != undefined /*&& file.type == "..."*/) this.getView().byId("artifactFileUploader").setValueState("Success");
            this.file = file;
        },
        handleUploadButtonPress: function (oEvent) {
            if (
                this.getView().byId("projectNameInput").getValueState() != "Success" &&
                this.getView().byId("artifactFileUploader").getValueState() != "Success") {
                MessageBox.error(
                    "Please fill all the fields correctly.",
                    {
                        title: "Information",
                        actions: [MessageBox.Action.OK]
                    }
                );
                return;
            }

            var jsonModel = this.getView().getModel();
            binaryFileToArrayBuffer(this.file, function (fileName, arrayBuffer) {
                var projectName = jsonModel.getProperty("/projectName");
                var ws = new WebSocket("ws://" + document.location.host + "/websocket/binary/" + projectName + "/" + fileName);
                ws.onopen = function () {
                    MessageToast.show("Websocket has been opened to " + projectName);
                    ws.send(arrayBuffer);
                };
            });
        }
    });

    return UploadController;
}, /* bExport= */ true);
function binaryFileToArrayBuffer(file, callback) {
    if (file) {
        var reader = new FileReader();

        // On load set file contents to text view
        reader.onload = function(oEvent) {
            callback(file.name, oEvent.target.result);
        };

        // Read in the file as text
        reader.readAsArrayBuffer(file);
    }
}
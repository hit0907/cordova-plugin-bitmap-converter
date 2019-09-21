var exec = require('cordova/exec');
var BitmapConverter = {

    // Convert image to base64 string
    convertImage: function (path, success, failure) {
        exec(success, failure, "BitmapConverter", "imagePath", [path]);
    },

    // Convert image to base64 string
    convertImageBase64: function (base64, success, failure) {
        exec(success, failure, "BitmapConverter", "imageBase64", [base64]);
    }
};

function cleanBreakLine(base64) {
    return base64.replace(/\n/g, '');
}

module.exports = BitmapConverter;

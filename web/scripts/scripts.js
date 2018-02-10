/**
 * Created by Caterina on 4/19/2016.
 */

function recaptchaCallback() {
    if (grecaptcha.getResponse() != "")
        document.getElementById("registerButton").disabled=false;
}


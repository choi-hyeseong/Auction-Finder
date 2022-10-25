

//start btn
$("#start").on("click", () => {
    $("#first_div").hide(1000);
    $("#second_div").show(2000);
});

//nearby find
$("#nearby").on("click", () => {
    $("#second_div").hide(1000);
    $("#nearby_div").show(2000);
});


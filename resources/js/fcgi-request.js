$(document).ready(function() {

    const VALID_CLASS = "is-valid"
    const INVALID_CLASS = "is-invalid"
    const NUMBER_REGEX = /^(\-)?[0-9]+(\.[0-9]{1,15})?$/

    const submitButton = $("#submit")

    const xInput = $(".x-input")
    const yInput = $(".y-input")
    const rInput = $(".r-input")

    changeValidityClass(xInput, checkXValid())
    changeValidityClass(yInput, checkYValid())
    changeValidityClass(rInput, checkRValid())

    function checkXValid() {
        let x = xInput.val()
        if (!NUMBER_REGEX.test(x)) return false
        x = parseFloat(x)
        if (!(x < 5 && x > -3)) return false
        return true
    }

    function checkYValid() {
        let y = yInput.val()
        if (!NUMBER_REGEX.test(y)) return false
        y = parseFloat(y)
        if (!(y < 3 && y > -5)) return false
        return true
    }

    function checkRValid() {
        let r = rInput.val()
        if (!NUMBER_REGEX.test(r)) return false
        r = parseFloat(r) 
        if (![1.0, 1.5, 2.0, 2.5, 3.0].includes(r)) return false
        return true
    }

    xInput.on("input", () => {changeValidityClass(xInput, checkXValid())})
    yInput.on("input", () => {changeValidityClass(yInput, checkYValid())})
    rInput.on("input", () => {changeValidityClass(rInput, checkRValid())})

    function changeValidityClass(object, check) {
        if (check) {
            object.removeClass(INVALID_CLASS)
            object.addClass(VALID_CLASS)
        } else {
            object.removeClass(VALID_CLASS)
            object.addClass(INVALID_CLASS)
        }
    }
        
    function request(x, y, r) {
        $.ajax({
            url: "/fcgi-bin/app-all.jar",
            type: "GET",
            dataType: "text",
            data: {
                r: r,
                x: x,
                y: y,
            },
            success: addResults
        })
    }

    function addResults(result) {
        $("#result").html(result)
    }

    function getInputs() {
        x = parseFloat(xInput.val())
        y = parseFloat(yInput.val())
        r = parseFloat(rInput.val())
        return {x: x, y: y, r: r}
    }

    submitButton.click((e) => {
        e.preventDefault()
        if (checkXValid && checkYValid && checkRValid) {
            inputs = getInputs()
            request(inputs.x, inputs.y, inputs.r)
        }       
    })
})
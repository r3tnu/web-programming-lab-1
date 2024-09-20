$(document).ready(function() {

    const VALID_CLASS = "is-valid"
    const INVALID_CLASS = "is-invalid"
    const NUMBER_REGEX = /^(\-)?[0-9]+(\.[0-9]{1,15})?$/

    const submitButton = $("#submit")

    const xInput = $(".x-input")
    const yInput = $(".y-input")
    const rInput = $(".r-input")

    var xIsValid 
    var yIsValid
    var rIsValid

    onXChange()
    onYChange()
    onRChange()

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

    xInput.on("input", onXChange)
    yInput.on("input", onYChange)
    rInput.on("input", onRChange)
        
    function onXChange() {
        xIsValid = checkXValid()
        if (xIsValid) {
            xInput.removeClass(INVALID_CLASS)
            xInput.addClass(VALID_CLASS)
        } else {
            xInput.removeClass(VALID_CLASS)
            xInput.addClass(INVALID_CLASS)
        }
    }

    function onYChange() {
        yIsValid = checkYValid()
        if (yIsValid) {
            yInput.removeClass(INVALID_CLASS)
            yInput.addClass(VALID_CLASS)
        } else {
            yInput.removeClass(VALID_CLASS)
            yInput.addClass(INVALID_CLASS)
        }
    }

    function onRChange() {
        rIsValid = checkRValid()
        if (rIsValid) {
            rInput.removeClass(INVALID_CLASS)
            rInput.addClass(VALID_CLASS)
        } else {
            rInput.removeClass(VALID_CLASS)
            rInput.addClass(INVALID_CLASS)
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
        if (xIsValid && yIsValid && rIsValid) {
            inputs = getInputs()
            request(inputs.x, inputs.y, inputs.r)
        }       
    })
})
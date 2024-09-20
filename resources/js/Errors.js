class PropertyValidationError extends Error {
    constructor(property) {
        super("Property \"" + property + "\" is invalid")
        this.name = "PropertyValidationError"
        this.property = property
    }
}

export {PropertyValidationError}


package com.inlacou.exinput.exceptions

import com.inlacou.exinput.exceptions.reasons.Reason

class InvalidException: Throwable {

    val reasons = mutableListOf<Reason>()

    constructor(reason: Reason) : super(reason.toString()) {
        reasons.add(reason)
    }
    constructor(vararg reasons: Reason) : super(reasons.toString()) {
        this.reasons.addAll(reasons)
    }
    constructor(reasons: List<Reason>) : super(reasons.toString()) {
        this.reasons.addAll(reasons)
    }

}
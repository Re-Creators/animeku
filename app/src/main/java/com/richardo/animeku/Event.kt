package com.richardo.animeku


/**
 * SingleEvent Wrapper for Live Data
 * from : https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 */
open class Event<out T>(private val content : T) {

    private var hasBeenhandled = false

    fun getContentIfNothandled(second : Boolean? = null) : T? {
        return if(hasBeenhandled){
            null
        }else {
            if (second != null) {
                hasBeenhandled = true
            }
            content
        }
    }

    fun peekContent() : T = content
}
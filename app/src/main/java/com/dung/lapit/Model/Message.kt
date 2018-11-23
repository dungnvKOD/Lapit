package com.dung.lapit.Model

class Message {

    var message: String? = null
    var time: Long = 0
    var myIdUser: String? = null
    var friendIdUser: String? = null
    var image: String? = null
    var urlAvatar: String? = null

    constructor()

    constructor(message: String?, time: Long, myIdUser: String?, friendIdUser: String?, image: String?) {
        this.message = message
        this.time = time
        this.myIdUser = myIdUser
        this.friendIdUser = friendIdUser
        this.image = image
    }


    constructor(message: String?, time: Long, myIdUser: String?, friendIdUser: String?) {
        this.message = message
        this.time = time
        this.myIdUser = myIdUser
        this.friendIdUser = friendIdUser
    }

    constructor(
        message: String?,
        time: Long,
        myIdUser: String?,
        friendIdUser: String?,
        image: String?,
        urlAvatar: String?
    ) {
        this.message = message
        this.time = time
        this.myIdUser = myIdUser
        this.friendIdUser = friendIdUser
        this.image = image
        this.urlAvatar = urlAvatar
    }


}
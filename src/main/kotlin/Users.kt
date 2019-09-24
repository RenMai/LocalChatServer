object Users {
    private val userList: HashSet<String> = hashSetOf()
    fun addUsername(username: String): Boolean {
        if (!isUsernameExist(username)) {
            //check duplicate username before add
            userList.add(username)
            return true
        }
        return false
    }

    fun removeUsername(username: String) {
        if (isUsernameExist(username))
            userList.remove(username)
    }

    private fun isUsernameExist(username: String): Boolean {
        return username in userList
    }

    fun getUserList(): List<String> {
        return userList.toList()
    }

    override fun toString(): String {
        val users = userList.toList()
        var formattedString = ""
        for (i in users) {
            formattedString += "\n $i"
        }
        return formattedString
    }
}
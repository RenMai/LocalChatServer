object Users
{
    private val userList: HashSet<String> = hashSetOf()

    fun addUsername(username: String): Boolean
    {
        if (!isUsernameExist(username))
        {
            userList.add(username)
            return true
        }
        return false
    }
    fun removeUsername(username: String)
    {
        if (isUsernameExist(username))
            userList.remove(username)
    }
    private fun isUsernameExist(username: String): Boolean
    {
        return username in userList
    }
    fun getUserList(): List<String>
    {
        return userList.toList()
    }
    override fun toString(): String
    {
        var users = userList.toList()
        var formattedString: String = ""
        for (i in users)
        {
            formattedString += "\n $i"
        }
        return formattedString
    }
}
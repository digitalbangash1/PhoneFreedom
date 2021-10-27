package dtu.projekt.phonefreedom.data

import dtu.projekt.phonefreedom.R
import dtu.projekt.phonefreedom.model.Message

class DataSource {

    fun loadMessages() : List<Message> {
        return mutableListOf(
            Message(R.string.standart),
            Message(R.string.children),
            Message(R.string.family),
            Message(R.string.friends),
            Message(R.string.work),
            Message(R.string.exam),
            Message(R.string.holiday),
            Message(R.string.free_time)
            )
    }

}
package sm_tv.com.l_tech.util

import android.widget.EditText
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

object MaskHelper {

    private fun createSlot(mask: String): Array<out Slot> {
        val correctMask = mask.replace("Х", "_", true)
        return UnderscoreDigitSlotsParser().parseSlots(correctMask)
    }

    private fun createFormatWatcher(slots: Array<out Slot>): FormatWatcher {
        return MaskFormatWatcher(MaskImpl.createTerminated(slots))
    }

    fun applyMask(mask: String?, edPhoneNumber: EditText) {
        val slots = mask?.let { createSlot(it) }
        val formatWatcher = slots?.let { createFormatWatcher(it) }
        formatWatcher?.installOn(edPhoneNumber)
    }

    fun getNumberPhone(phone: String): String {
        val re = Regex("[^0-9]")
        return re.replace(phone, "")
    }

}

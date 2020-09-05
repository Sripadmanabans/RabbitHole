package com.adjectivemonk2.rabbithole.timber

import android.util.Log
import timber.log.Timber
import timber.log.Tree
import java.io.PrintWriter
import java.io.StringWriter
import java.util.regex.Matcher
import java.util.regex.Pattern
import kotlin.math.min

class LogcatTree : Tree() {

  override fun performLog(priority: Int, tag: String?, throwable: Throwable?, message: String?) {
    val safeTag = tag.asSafeTag()

    val threadName = Thread.currentThread().name
    val fullMessage = if (message != null) {
      if (throwable != null) {
        "{$threadName} $message\n${throwable.stackTraceString}"
      } else {
        "{$threadName} $message"
      }
    } else {
      throwable?.stackTraceString ?: return
    }

    val length = fullMessage.length
    if (length <= MAX_LOG_LENGTH) {
      // Fast path for small messages which can fit in a single call.
      if (priority == Timber.ASSERT) {
        Log.wtf(safeTag, fullMessage)
      } else {
        Log.println(priority, safeTag, fullMessage)
      }
      return
    }

    // Slow path: Split by line, then ensure each line can fit into Log's maximum length.
    // TODO use lastIndexOf instead of indexOf to batch multiple lines into single calls.
    var i = 0
    while (i < length) {
      var newline = fullMessage.indexOf('\n', i)
      newline = if (newline != -1) newline else length
      do {
        val end = min(newline, i + MAX_LOG_LENGTH)
        val part = fullMessage.substring(i, end)
        if (priority == Log.ASSERT) {
          Log.wtf(safeTag, part)
        } else {
          Log.println(priority, safeTag, part)
        }
        i = end
      } while (i < newline)
      i++
    }
  }

  private fun createStackElementTag(element: StackTraceElement): String {
    var tag = element.className
    val matcher: Matcher = ANONYMOUS_CLASS.matcher(tag)
    if (matcher.find()) {
      tag = matcher.replaceAll("")
    }
    tag = tag.substring(tag.lastIndexOf('.') + 1)
    return tag
  }

  private fun String?.asSafeTag(): String {
    // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
    // because Robolectric runs them on the JVM but on Android the elements are different.
    val stackTrace = Throwable().stackTrace
    check(stackTrace.size > CALL_STACK_INDEX) {
      "Synthetic stacktrace didn't have enough elements: are you using proguard?"
    }
    val tag = this ?: createStackElementTag(stackTrace[CALL_STACK_INDEX])
    // Tag length limit was removed in API 24.
    if (tag.length > MAX_TAG_LENGTH) {
      return tag.substring(0, MAX_TAG_LENGTH)
    }
    return tag
  }

  private val Throwable.stackTraceString
    get(): String {
      // DO NOT replace this with Log.getStackTraceString() - it hides UnknownHostException, which is
      // not what we want.
      val sw = StringWriter(STRING_WRITER_INITIAL_SIZE)
      val pw = PrintWriter(sw, false)
      printStackTrace(pw)
      pw.flush()
      return sw.toString()
    }

  companion object {
    private const val STRING_WRITER_INITIAL_SIZE = 256
    private const val MAX_LOG_LENGTH = 4000
    private const val MAX_TAG_LENGTH = 23
    private const val CALL_STACK_INDEX = 4
    private val ANONYMOUS_CLASS: Pattern = Pattern.compile("(\\$\\d+)+$")
  }
}

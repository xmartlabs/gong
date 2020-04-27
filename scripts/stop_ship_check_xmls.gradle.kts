tasks.register("checkStopShipXmls") {
  doLast {
    val process = ProcessBuilder("grep", "-ilR", "--include", "*.xml", "STOPSHIP", "app/src/").start()
    process.inputStream.reader(Charsets.UTF_8).use {
      val files = it.readText()
      if (files.isNotBlank()) {
        throw IllegalStateException("Forbidden comment found (STOPSHIP), files:\n $files")
      }
    }
    process.waitFor(10, TimeUnit.SECONDS)
  }
}

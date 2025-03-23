POST("https://pgtune.sainth.de/api/configuration") {
    header("Content-Type", "application/json")
    header("Accept", "text/plain")
    body("""
        {
          "dbVersion":"V13",
          "osType":"Linux",
          "dbApplication":"WEB",
          "ram":{
            "memory":14,
            "unit":"GB"
            },
            "cores":8,
            "connections":60,
            "dataStorage":"SSD"
        }
    """.trimIndent())
}

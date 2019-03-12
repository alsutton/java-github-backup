java_binary(
    name = "GitHubBackup",
    srcs = glob(["src/main/java/com/alsutton/backup/github/*.java"]),
    create_executable = 1,
    main_class = "com.alsutton.backup.github.Main",
    deps = [
        "eclipse_github",
        "jgit",
        "@commons_cli//jar",
    ],
)

java_library(
    name = "eclipse_github",
    exports = [
        "@com_google_code_gson//jar",
        "@org_eclipse_egit_github_core//jar",
    ],
)

java_library(
    name = "jgit",
    exports = [
        "@bcpg_jdk15on//jar",
        "@bcpkix_jdk15on//jar",
        "@bcprov_jdk15on//jar",
        "@eclipse_jgit//jar",
        "@javaewah//jar",
        "@jsch//jar",
        "@jzlib//jar",
        "@log_api//jar",
    ],
)

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("com.google.devtools.ksp").version("1.6.10-1.0.4")
}

dependencies {
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation("com.squareup.moshi:moshi-adapters:1.12.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")

    implementation("joda-time:joda-time:2.12.5")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.0")
}

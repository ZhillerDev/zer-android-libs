plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)

  id("maven-publish")
}

android {
  namespace = "io.zhiller.mathlib"
  compileSdk = 34

  defaultConfig {
    minSdk = 30

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
  kotlinOptions {
    jvmTarget = "1.8"
  }
}

dependencies {

  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.material)
  testImplementation(libs.junit)
  androidTestImplementation(libs.androidx.junit)
  androidTestImplementation(libs.androidx.espresso.core)
}

val GROUP_ID = "com.github.zhiller"
val ARTIFACT_ID = "test-lib"
val VERSION = "v1.0.0"

//val VERSION = latestGitTag().ifEmpty { "1.0.0" }
//fun latestGitTag(): String {
//  val process = ProcessBuilder("git", "describe", "--tags", "--abbrev=0").start()
//  return  process.inputStream.bufferedReader().use {bufferedReader ->
//    bufferedReader.readText().trim()
//  }
//}

publishing { // 发布配置
  publications {
    register<MavenPublication>("release") { // 注册一个名字为 release 的发布内容
      groupId = GROUP_ID
      artifactId = ARTIFACT_ID
      version = VERSION
      afterEvaluate { // 在所有的配置都完成之后执行
        // 从当前 module 的 release 包中发布
        from(components["release"])
      }
    }
  }
}
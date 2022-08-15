@file:Suppress("SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection",
    "SpellCheckingInspection", "SpellCheckingInspection", "SpellCheckingInspection"
)

import org.gradle.api.artifacts.dsl.DependencyHandler

object AppDependencies {
    // Android UI
    private const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    private const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    private const val glideKapt = "com.github.bumptech.glide:compiler:${Versions.glide}"
    private const val material = "com.google.android.material:material:${Versions.material}"
    private const val navigationFragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    private const val navigationUI =
        "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    private const val paging = "androidx.paging:paging-runtime:${Versions.paging}"
    private const val swipeRefreshLayout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"

    // Libs
    private const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    private const val commonJava8Kapt =
        "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    private const val coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    private const val daggerHilt = "com.google.dagger:hilt-android:${Versions.daggerHilt}"
    private const val daggerHiltCompilerKapt =
        "com.google.dagger:hilt-compiler:${Versions.daggerHilt}"
    private const val gson = "com.google.code.gson:gson:${Versions.gson}"
    private const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    private const val lifecycleRuntime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    private const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    private const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    private const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    private const val room = "androidx.room:room-runtime:${Versions.room}"
    private const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    private const val roomPaging = "androidx.room:room-paging:${Versions.room}"
    private const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    private const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Test Libs
    private const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
    private const val coroutinesTest =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    private const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private const val extJUnit = "androidx.test.ext:junit:${Versions.extJunit}"
    private const val junit = "junit:junit:${Versions.junit}"
    private const val mockito = "org.mockito.kotlin:mockito-kotlin:${Versions.mockito}"
    private const val okhttpMockWebServer =
        "com.squareup.okhttp3:mockwebserver:${Versions.okhttpMockWebServer}"
    private const val pagingTesting = "androidx.paging:paging-common:${Versions.paging}"
    private const val roomKapt = "androidx.room:room-compiler:${Versions.room}"
    private const val truth = "com.google.truth:truth:${Versions.truth}"
    private const val turbine = "app.cash.turbine:turbine:${Versions.turbine}"

    val appModuleLibraries = listOf(
        coreKtx,
        appcompat,
        constraintLayout,
        daggerHilt,
        fragment,
        glide,
        lifecycleRuntime,
        material,
        navigationFragment,
        navigationUI,
        paging,
        swipeRefreshLayout,
        viewModel
    )

    val androidTestLibraries = listOf(
        espresso,
        extJUnit,
        turbine,
        truth
    )

    val apiLibraries = listOf(
        coroutines,
        timber
    )

    val kaptAppLibraries = listOf(
        daggerHiltCompilerKapt,
        glideKapt
    )

    val kaptLibraries = listOf(
        commonJava8Kapt,
        daggerHiltCompilerKapt,
        roomKapt
    )

    val sharedModuleLibraries = listOf(
        appcompat,
        coreKtx,
        daggerHilt,
        gson,
        gsonConverter,
        okhttp,
        okhttpLogging,
        paging,
        retrofit,
        room,
        roomKtx,
        roomPaging
    )

    val testAppLibraries = listOf(
        coreTesting,
        coroutinesTest,
        junit,
        mockito,
        pagingTesting,
        truth,
        turbine
    )

    val testSharedLibraries = listOf(
        coreTesting,
        coroutinesTest,
        junit,
        mockito,
        okhttpMockWebServer,
        pagingTesting,
        truth,
        turbine
    )
}

// Util functions for adding the different type dependencies from build.gradle file
fun DependencyHandler.kapt(list: List<String>) {
    list.forEach { dependency ->
        add("kapt", dependency)
    }
}

fun DependencyHandler.implementation(list: List<String>) {
    list.forEach { dependency ->
        add("implementation", dependency)
    }
}

fun DependencyHandler.api(list: List<String>) {
    list.forEach { dependency ->
        add("api", dependency)
    }
}

fun DependencyHandler.androidTestImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("androidTestImplementation", dependency)
    }
}

fun DependencyHandler.testImplementation(list: List<String>) {
    list.forEach { dependency ->
        add("testImplementation", dependency)
    }
}
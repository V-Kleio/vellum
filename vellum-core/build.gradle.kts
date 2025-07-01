dependencies {
    // LWJGL BOM for version management
    api(platform(libs.lwjgl.bom))
    
    // Core LWJGL dependencies
    api(libs.bundles.lwjgl.core)
    
    // Platform-specific natives
    runtimeOnly("org.lwjgl:lwjgl::natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-glfw::natives-linux")
    runtimeOnly("org.lwjgl:lwjgl-opengl::natives-linux")
    
    runtimeOnly("org.lwjgl:lwjgl::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-glfw::natives-windows")
    runtimeOnly("org.lwjgl:lwjgl-opengl::natives-windows")
    
    runtimeOnly("org.lwjgl:lwjgl::natives-macos")
    runtimeOnly("org.lwjgl:lwjgl-glfw::natives-macos")
    runtimeOnly("org.lwjgl:lwjgl-opengl::natives-macos")
    
    // Optional utilities for math operations
    api(libs.commons.math3)
}
package gay.pyrrha.build

import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.invoke

internal fun configureGradleManagedDevices(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
) {
    // why use atd images: https://developer.android.com/studio/test/gradle-managed-devices#atd-optimizations
    val pixel8Pro = DeviceConfig("Pixel 8 Pro", 34, "aosp")
    val pixel8 = DeviceConfig("Pixel 8", 34, "aosp-atd")
    val pixel7Pro = DeviceConfig("Pixel 7 Pro", 33, "aosp")
    val pixel7 = DeviceConfig("Pixel 7", 33, "aosp-atd")

    val allDevices = listOf(pixel8Pro, pixel8,  pixel7Pro, pixel7)
    val ciDevices = listOf(pixel8, pixel7)

    commonExtension.testOptions {
        managedDevices {
            devices {
                allDevices.forEach { deviceConfig ->
                    maybeCreate(deviceConfig.taskName, ManagedVirtualDevice::class.java).apply {
                        device = deviceConfig.device
                        apiLevel = deviceConfig.apiLevel
                        systemImageSource = deviceConfig.systemImageSource
                    }
                }
            }
            groups {
                maybeCreate("ci").apply {
                    ciDevices.forEach { deviceConfig ->
                        targetDevices.add(devices[deviceConfig.taskName])
                    }
                }
            }
        }
    }
}

private data class DeviceConfig(
    val device: String,
    val apiLevel: Int,
    val systemImageSource: String,
) {
    val taskName = buildString {
        append(device.lowercase().replace(" ", ""))
        append("api")
        append(apiLevel.toString())
        append(systemImageSource.replace("-", ""))
    }
}
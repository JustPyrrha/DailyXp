package gay.pyrrha.build

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.ApplicationProductFlavor
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.ProductFlavor

@Suppress("EnumEntryName")
enum class FlavourDimension {
    contentType
}

@Suppress("EnumEntryName")
enum class XpFlavour(val dimension: FlavourDimension, val applicationIdSuffix: String? = null) {
    demo(FlavourDimension.contentType, applicationIdSuffix = ".demo"),
    prod(FlavourDimension.contentType)
}

fun configureFlavours(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    block: ProductFlavor.(flavour: XpFlavour) -> Unit = {}
) {
    commonExtension.apply {
        flavorDimensions += FlavourDimension.contentType.name
        productFlavors {
            XpFlavour.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    block(this, it)
                    if(this@apply is ApplicationExtension && this is ApplicationProductFlavor) {
                        applicationIdSuffix = it.applicationIdSuffix
                    }
                }
            }
        }
    }
}

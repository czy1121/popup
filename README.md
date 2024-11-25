# Popup

简化 PopupWindow 使用


## Gradle

``` groovy
repositories {
    maven { url "https://gitee.com/ezy/repo/raw/cosmo/"}
}
dependencies {
    implementation "me.reezy.cosmo:popup:0.10.0"
}
```

## 使用

```kotlin 
Popup(view, width, height).show(anchorView, Popup.VPosition.ABOVE, Popup.HPosition.ALIGN_LEFT, xOffset, yOffset)
```

## LICENSE

The Component is open-sourced software licensed under the [Apache license](LICENSE).
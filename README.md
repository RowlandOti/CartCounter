### Status
[![Build Status](https://travis-ci.org/RowlandOti/CartCounter.svg?branch=master)](https://travis-ci.org/RowlandOti/CartCounter)[ ![Download](https://api.bintray.com/packages/rowlandoti/maven/CartCounter/images/download.svg) ](https://bintray.com/rowlandoti/maven/CartCounter/_latestVersion)[![contributions welcome](https://img.shields.io/badge/contributions-welcome-brightgreen.svg?style=flat)](https://github.com/dwyl/esta/issues)[![Methods & Size](https://img.shields.io/badge/Methods%20and%20size-98%20|%2025%20KB-e91e63.svg)](http://www.methodscount.com/?lib=com.rowland.cartcounter%3ACartCounter%3A1.0.0-beta-1)
# CartCounter
Add a cart icon with item counter to the Toolbar. You can increment the counter based on the number of unique items the user has in the shopping cart. In addition, you can animate the icon when there is an increment or decrement.
* Customize the cart icon and icon color
* Animate the cart icon on changing value

## Download
Grab the latest version via Maven:
```xml
<dependency>
  <groupId>com.rowland.cartcounter</groupId>
  <artifactId>CartCounter</artifactId>
  <version>1.0.0-beta-1</version>
  <type>pom</type>
</dependency>
```
or Gradle:
```groovy
repositories {
  jcenter()
}

dependencies {
  compile 'com.rowland.cartcounter:CartCounter:1.0.0-beta-1'
}
```

## Usage
Usage is very simple and easy to incorporate. You can use it like below

>res/menu/menu_main.xml
```xml
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item
        android:id="@+id/action_addcart"
        android:icon="@drawable/ic_shopping_cart_white_48dp"
        android:title="Add Cart"
        app:actionLayout="@layout/menu_cartcount"
        app:showAsAction="always"></item>

</menu>
```

Thereafter you can load your `Menu` and associate it with the `CartCounterActionView` in code like below
```kotlin
override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

override fun onPrepareOptionsMenu(menu: Menu): Boolean {
    val itemData = menu.findItem(R.id.action_addcart)
    val actionView = itemData.actionView as CartCounterActionView
    actionView.setItemData(menu, itemData)
    actionView.setCount(cartCount)
    return super.onPrepareOptionsMenu(menu)
}
```

If you would like to override the background color of the count `TextView`, you can do so in your styles include the `cc_count_color` attribute. For example:
```xml
<!-- Base application theme. -->
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
    <!-- Customize your theme here. -->
    <item name="colorPrimary">@color/colorPrimary</item>
    <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
    <item name="colorAccent">@color/colorAccent</item>

    <item name="cc_count_color">@color/colorAccent</item>
</style>
```

## Preview

![Alt text](https://github.com/RowlandOti/CartCounter/blob/master/documentation/illustration/preview.gif?raw=true "CartCounter Preview")        

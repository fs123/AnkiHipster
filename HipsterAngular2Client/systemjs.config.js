/**
 * System configuration for Angular 2 samples
 * Adjust as necessary for your application needs.
 */
(function (global) {
  System.config({
    paths: {
      // paths serve as alias
      'npm:': 'node_modules/'
    },
    // map tells the System loader where to look for things
    map: {
      // our app is within the app folder
      app: 'app',

      // angular bundles
      '@angular/core': 'npm:@angular/core/bundles/core.umd.js',
      '@angular/common': 'npm:@angular/common/bundles/common.umd.js',
      '@angular/compiler': 'npm:@angular/compiler/bundles/compiler.umd.js',
      '@angular/platform-browser': 'npm:@angular/platform-browser/bundles/platform-browser.umd.js',
      '@angular/platform-browser-dynamic': 'npm:@angular/platform-browser-dynamic/bundles/platform-browser-dynamic.umd.js',
      '@angular/http': 'npm:@angular/http/bundles/http.umd.js',
      '@angular/router': 'npm:@angular/router/bundles/router.umd.js',
      '@angular/forms': 'npm:@angular/forms/bundles/forms.umd.js',

      // other libraries
      'rxjs':                       'npm:rxjs',
      'angular2-in-memory-web-api': 'npm:angular2-in-memory-web-api',
      '@ngrx': 'node_modules/@ngrx',
      'ng2-slider-component': 'node_modules/ng2-slider-component',
      'ng2-slideable-directive/slideable.directive': 'node_modules/ng2-slideable-directive/slideable.directive.js',
      'ng2-styled-directive/ng2-styled.directive': 'node_modules/ng2-styled-directive/ng2-styled.directive.js'

    },
    // packages tells the System loader how to load when no filename and/or no extension
    packages: {
      app: {
        main: './main.js',
        defaultExtension: 'js'
      },
      rxjs: {
        defaultExtension: 'js'
      },
      'angular2-in-memory-web-api': {
        main: './index.js',
        defaultExtension: 'js'
      },
      '@ngrx/core': {
        main: 'index.js',
        format: 'cjs'
      },
      '@ngrx/store': {
        main: 'index.js',
        format: 'cjs'
      },
      'node_modules/ng2-slider-component': {
        main: 'ng2-slider.component.system.js',
        defaultExtension: 'system.js'
      },
    }
  });
})(this);

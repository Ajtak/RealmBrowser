# RealmBrowser
Browser to view the table structure and content of your database.
## Usage
**Step 1. Add the JitPack repository to your build file** 
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

**Step 2. Add the dependency**

	dependencies {
		compile 'com.github.User:Repo:Tag'
	}

**Step 3. Install to project**
*Minimal configuration*    
```java
if (BuildConfig.DEBUG) {
  RealmBrowser rb = new RealmBrowser();
  rb.start();
}
```
##ToDo
- [ ] Pagination for content
- [ ] Editation of rows
   



## Screenshots
![alt text](http://blog.jafr.eu/domains/blog.jafr.eu/wp-content/uploads/2017/03/Snímek-obrazovky-2017-03-05-v-15.51.34.png "Table of structure")

![alt text](http://blog.jafr.eu/domains/blog.jafr.eu/wp-content/uploads/2017/03/Snímek-obrazovky-2017-03-05-v-15.51.41.png "Table of content")

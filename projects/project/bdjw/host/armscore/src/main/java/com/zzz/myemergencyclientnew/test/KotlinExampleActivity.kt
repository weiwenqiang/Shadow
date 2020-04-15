/*
 * Copyright 2015 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.realm.examples.kotlin

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.zzz.myemergencyclientnew.R
import io.realm.Realm
import io.realm.Sort
import io.realm.examples.kotlin.model.Cat
import io.realm.examples.kotlin.model.Dog
import io.realm.examples.kotlin.model.Person
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class KotlinExampleActivity : Activity() {
    companion object {
        const val TAG: String = "KotlinExampleActivity"
    }

    private lateinit var rootLayout: LinearLayout
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_realm_basic_example)

        rootLayout = findViewById(R.id.container)
        rootLayout.removeAllViews()

        // 开放的领域UI线程
        realm = Realm.getDefaultInstance()

        // 删除所有人
        // 减少代码大小和不可能
        // 忘了提交事务
        realm.executeTransaction { realm ->
            realm.deleteAll()
        }

        // 这些操作是足够小
        // 通常我们可以安全地在UI线程上运行它们。
        basicCRUD(realm)
        basicQuery(realm)
        basicLinkQuery(realm)

        // 可以在另一个线程执行更复杂的操作,例如使用
        // 像的doAsync扩展方法。
        doAsync {
            var info = ""

            //打开默认域。所有线程都必须使用自己的引用领域。
            //跨线程不能被转移。

            //领域实现了可闭接口,因此
            //我们可以利用芬兰湾的科特林的内置扩展方法的使用(双关语)。
            Realm.getDefaultInstance().use { realm ->
                info += complexReadWrite(realm)
                info += complexQuery(realm)
            }
            uiThread {
                showStatus(info)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close() // 使用完关闭.
    }

    private fun showStatus(text: String) {
        Log.i(TAG, text)
        val textView = TextView(this)
        textView.text = text
        rootLayout.addView(textView)
    }

    @Suppress("NAME_SHADOWING")
    private fun basicCRUD(realm: Realm) {
        showStatus("执行基本的创建/读取/更新/删除(CRUD)操作……")

        // 所有写必须包装在一个事务促进安全多线程
        realm.executeTransaction { realm ->
            // 添加一个人
            val person = realm.createObject<Person>(0)
            person.name = "年轻人"
            person.age = 14
        }

        // 找到第一个(没有查询条件)和阅读一个字段
        val person = realm.where<Person>().findFirst()!!
        showStatus(person.name + ": " + person.age)

        // 人在一个事务中更新
        realm.executeTransaction { _ ->
            person.name = "高级人"
            person.age = 99
            showStatus(person.name + " 长大: " + person.age)
        }
    }

    private fun basicQuery(realm: Realm) {
        showStatus("\n执行基本查询操作...")
        showStatus("人的数量: ${realm.where<Person>().count()}")

        val ageCriteria = 99
        val results = realm.where<Person>().equalTo("age", ageCriteria).findAll()

        showStatus("结果集大小: " + results.size)
    }

    private fun basicLinkQuery(realm: Realm) {
        showStatus("\n执行基本链接查询操作...")
        showStatus("人的数量: ${realm.where<Person>().count()}")

        val results = realm.where<Person>().equalTo("cats.name", "Tiger").findAll()

        showStatus("结果集大小: ${results.size}")
    }

    private fun complexReadWrite(realm: Realm): String {
        var status = "\n执行复杂的读/写操作..."

        // Add ten persons in one transaction
        realm.executeTransaction {
            val fido = realm.createObject<Dog>()
            fido.name = "菲多"
            for (i in 1..9) {
                val person = realm.createObject<Person>(i.toLong())
                person.name = "人没有. $i"
                person.age = i
                person.dog = fido

                // The field tempReference is annotated with @Ignore.
                // This means setTempReference sets the Person tempReference
                // field directly. The tempReference is NOT saved as part of
                // the RealmObject:
                person.tempReference = 42

                for (j in 0..i - 1) {
                    val cat = realm.createObject<Cat>()
                    cat.name = "猫_$j"
                    person.cats.add(cat)
                }
            }
        }

        // Implicit read transactions allow you to access your objects
        status += "\n人的数量: ${realm.where<Person>().count()}"

        // Iterate over all objects
        for (person in realm.where<Person>().findAll()) {
            val dogName: String = person?.dog?.name ?: "没有"

            status += "\n${person.name}: ${person.age} : $dogName : ${person.cats.size}"

            // The field tempReference is annotated with @Ignore
            // Though we initially set its value to 42, it has
            // not been saved as part of the Person RealmObject:
            check(person.tempReference == 0)
        }

        // Sorting
        val sortedPersons = realm.where<Person>().sort(Person::age.name, Sort.DESCENDING).findAll()
        status += "\n排序 ${sortedPersons.last()?.name} == ${realm.where<Person>().findAll().first()?.name}"

        return status
    }

    private fun complexQuery(realm: Realm): String {
        var status = "\n\n执行复杂的查询操作..."

        status += "\n人的数量: ${realm.where<Person>().count()}"

        // Find all persons where age between 7 and 9 and name begins with "Person".
        val results = realm.where<Person>()
            .between("age", 7, 9)       // Notice implicit "and" operation
            .beginsWith("name", "Person")
            .findAll()

        status += "\n结果集大小: ${results.size}"

        return status
    }
}

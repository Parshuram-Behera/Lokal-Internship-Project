package com.parshurambehera.lokalassignment.activities
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.parshurambehera.lokalassignment.R
import com.parshurambehera.lokalassignment.databinding.ActivityMainBinding
import com.parshurambehera.lokalassignment.fragments.BookmarkFragment
import com.parshurambehera.lokalassignment.fragments.JobFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var currentSelectedItem = R.id.menu_jobs
        replaceFragment(JobFragment())


        binding.bottomNavigationView.setOnItemSelectedListener {

            if (it.itemId.equals(R.id.menu_jobs)) {

                replaceFragment(JobFragment())
            } else {
                replaceFragment(BookmarkFragment())
            }

            return@setOnItemSelectedListener true

        }

        supportFragmentManager.addOnBackStackChangedListener {

            val currentFragmentName =
                supportFragmentManager.findFragmentById(R.id.mainFrameLayout)?.javaClass?.simpleName
                    ?: "Unknown"

            val newItemId = when (currentFragmentName) {
                "JobFragment" -> R.id.menu_jobs
                "BookmarkFragment" -> R.id.menu_bookMark
                else -> Log.d("Listener Tag Stack", "Nothing")
            }

            if (newItemId != currentSelectedItem) {
                currentSelectedItem = newItemId
                binding.bottomNavigationView.selectedItemId = newItemId
            }
        }


    }

    fun replaceFragment(fragment: Fragment) {

        val fragmentTag = fragment.javaClass.simpleName
        val fragmentManager = supportFragmentManager

        val currentFragment = fragmentManager.findFragmentByTag(fragmentTag)

        if (currentFragment == null) {

            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.mainFrameLayout, fragment, fragmentTag)
            transaction.addToBackStack(fragmentTag)
            transaction.commit()
        } else {
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.mainFrameLayout, fragment, fragmentTag)
            transaction.commit()
        }

    }


    override fun onBackPressed() {


        if (supportFragmentManager.backStackEntryCount > 1) {
            supportFragmentManager.popBackStack()
        }
        if ((supportFragmentManager.findFragmentById(R.id.mainFrameLayout)?.javaClass?.simpleName
                ?: "Unknown") == "JobFragment"
        ) {
            finishAffinity()
            super.onBackPressed()


        }
    }
}
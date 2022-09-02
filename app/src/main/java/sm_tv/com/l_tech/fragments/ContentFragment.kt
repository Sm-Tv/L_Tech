package sm_tv.com.l_tech.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nl.bryanderidder.themedtogglebuttongroup.ThemedButton
import nl.bryanderidder.themedtogglebuttongroup.ThemedToggleButtonGroup
import sm_tv.com.l_tech.R
import sm_tv.com.l_tech.adapters.ContentAdapter
import sm_tv.com.l_tech.fragments.models.Item
import sm_tv.com.l_tech.viewmodels.MainVM

class ContentFragment : Fragment() {

    private lateinit var viewModel: MainVM
    private lateinit var sortGroup: ThemedToggleButtonGroup
    private lateinit var recyclerItem: RecyclerView
    private lateinit var adapter: ContentAdapter
    lateinit var mainHandler: Handler

    private val updateData = object : Runnable {
        override fun run() {
            getListItem()
            mainHandler.postDelayed(this, DELAY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        init(view)
        observerPerItem()
        clickSortGroup()

        return view
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacks(updateData)
    }

    override fun onResume() {
        super.onResume()
        mainHandler.post(updateData)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> {
                getListItem()
            }
        }
        return true
    }

    private fun init(view: View) {
        viewModel = ViewModelProvider(this)[MainVM::class.java]
        sortGroup = view.findViewById(R.id.sortGroup)
        sortGroup.selectButton(R.id.serverSort)
        adapter = ContentAdapter()
        recyclerItem = view.findViewById(R.id.recyclerItem)
        recyclerItem.layoutManager = LinearLayoutManager(requireContext())
        recyclerItem.adapter = adapter
        mainHandler = Handler(Looper.getMainLooper())
    }

    private fun getListItem() {
        viewModel.getListItem()
    }

    private fun observerPerItem() {
        viewModel.listItem.observe(viewLifecycleOwner, Observer {
            updateData(it, false)
        })
    }

    private fun clickSortGroup() {
        sortGroup.apply {
            setOnSelectListener { button: ThemedButton ->
                when (button.id) {
                    R.id.serverSort -> {
                        val item = viewModel.listItem.value
                        updateData(item, false)
                    }
                    R.id.dataSort -> {
                        val item = viewModel.listItem.value
                        updateData(item, true)
                    }
                }
            }
        }
    }

    private fun updateData(item: List<Item>?, flag: Boolean) {
        adapter.setSortItems(item!!, flag)
        recyclerItem.smoothScrollToPosition(0)
    }

    companion object {
        const val DELAY = 300000L
    }
}

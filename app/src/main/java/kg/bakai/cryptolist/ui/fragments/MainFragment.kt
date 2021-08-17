package kg.bakai.cryptolist.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import kg.bakai.cryptolist.R
import kg.bakai.cryptolist.databinding.FragmentMainBinding
import kg.bakai.cryptolist.ui.adapters.CryptoAdapter
import kg.bakai.cryptolist.ui.viewmodels.MainViewModel
import kg.bakai.cryptolist.utils.Status
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel by viewModel<MainViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.fetchList("usd", 20)
        val adapter = CryptoAdapter()
        binding.apply {
            recyclerView.adapter = adapter
            swipeLayout.setOnRefreshListener {
                mainViewModel.fetchList("usd", 20)
            }
            mainViewModel.status.observe(viewLifecycleOwner) { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        swipeLayout.isRefreshing = false
                    }
                    Status.LOADING -> {
                        swipeLayout.isRefreshing = true
                    }
                    Status.ERROR -> {
                        swipeLayout.isRefreshing = false
                        Toast.makeText(requireContext(), resource.getErrorMessage(requireContext()), Toast.LENGTH_SHORT).show()
                    }
                }
            }

            mainViewModel.listInDatabase.observe(viewLifecycleOwner) {
                adapter.submitItems(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_search_item -> {
                findNavController().navigate(R.id.action_mainFragment_to_searchFragment)
            }
            R.id.menu_delete_item -> {
                mainViewModel.clearDb()
            }
        }
        return true
    }

}
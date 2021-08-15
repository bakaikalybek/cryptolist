package kg.bakai.cryptolist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import kg.bakai.cryptolist.databinding.FragmentSearchBinding
import kg.bakai.cryptolist.ui.adapters.CryptoAdapter
import kg.bakai.cryptolist.ui.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {
   private lateinit var binding: FragmentSearchBinding
   private val searchViewModel by viewModel<SearchViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = CryptoAdapter()
        binding.apply {
            recyclerViewSearch.adapter = adapter
            etSearch.doAfterTextChanged {
                if (it.isNullOrEmpty()) {
                    adapter.submitItems(emptyList())
                } else {
                    searchViewModel.searchCrypto(it.toString())
                }
            }

            searchViewModel.searchResult.observe(viewLifecycleOwner) {
                adapter.submitItems(it)
            }
        }

    }

}
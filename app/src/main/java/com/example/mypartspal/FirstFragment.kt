package com.example.mypartspal

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mypartspal.databinding.FragmentFirstBinding
import com.example.mypartspal.pbapi.Project
import com.example.mypartspal.pbapi.PartsBoxAPI
import com.example.mypartspal.pbapi.Projects
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), ProjectRecyclerAdapter.ItemClickListener {

    private var _binding: FragmentFirstBinding? = null
    private var _results: List<Project>? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val projects: PartsBoxAPI = PartsBoxAPI()
    private val callback = object : Callback<Projects> {
        override fun onFailure(call: Call<Projects>?, t:Throwable?) {
            Log.e("MainActivity", "Problem calling Github API {${t?.message}}")
        }

        override fun onResponse(call: Call<Projects>?, response: Response<Projects>?) {
            response?.isSuccessful.let {
                //_results = ProjectData(response?.body()?.data ?: emptyList())
                if (_binding != null) {
                    //binding.projectsRecycler.adapter = ProjectRecyclerAdapter(resultList)
                }
            }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        projects.getProjects(callback)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _results = ArrayList<Project>()
        (_results as ArrayList<Project>).add(Project("18ezxxywxwjpvbvthe1jbbae2z", "Test Project"))

        val rv: RecyclerView = binding.projectsRecycler
        rv.layoutManager = LinearLayoutManager(view.context)
        val adapter = ProjectRecyclerAdapter(view.context, _results as ArrayList<Project>, this)
        rv.adapter = adapter

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.w("", "clicked: " + _results!![position].project_id)
        val b = Bundle()
        b.putString("projectId", _results!![position].project_id)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, b)
    }
}
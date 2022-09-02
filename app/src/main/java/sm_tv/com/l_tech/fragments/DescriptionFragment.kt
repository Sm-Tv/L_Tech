package sm_tv.com.l_tech.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import sm_tv.com.l_tech.R
import sm_tv.com.l_tech.util.Constants.BASE_URL

class DescriptionFragment : Fragment() {

    private val args by navArgs<DescriptionFragmentArgs>()
    private lateinit var imageDFragment: ImageView
    private lateinit var tvTitleDFragment: TextView
    private lateinit var tvDescriptionDFragment: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)
        init(view)
        showContent()
        (activity as AppCompatActivity).supportActionBar?.title = args.title

        return view
    }

    private fun init(view: View) {
        imageDFragment = view.findViewById(R.id.imageDFragment)
        tvTitleDFragment = view.findViewById(R.id.tvTitleDFragment)
        tvDescriptionDFragment = view.findViewById(R.id.tvDescriptionDFragment)
    }

    private fun showContent(){
        tvTitleDFragment.text = args.title
        tvDescriptionDFragment.text = args.description
        val uri = Uri.parse(BASE_URL + args.imageUri)
        Picasso.get()
            .load(uri)
            .resize(300, 300)
            .error(R.drawable.ic_baseline_refresh_24)
            .into(imageDFragment, object : Callback {
                override fun onSuccess() {
                    println("__________________ONSUCCESS")
                }

                override fun onError(e: Exception?) {
                    println("____________________$e")
                }
            })
    }
}

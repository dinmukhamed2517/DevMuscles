package kz.just_code.devmuscles.fragments



import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.devmuscles.base.BaseFragment
import kz.just_code.devmuscles.base.SharedViewModel
import kz.just_code.devmuscles.databinding.FragmentUserInformationBinding
import kz.just_code.devmuscles.firebase.User
import kz.just_code.devmuscles.firebase.UserDao
import javax.inject.Inject


@AndroidEntryPoint
class UserInformationFragment:BaseFragment<FragmentUserInformationBinding>(FragmentUserInformationBinding::inflate) {
    private val viewModel: SharedViewModel by activityViewModels()
    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var userDao: UserDao

    override var showBottomNavigation: Boolean = false

    override fun onBindView() {
        super.onBindView()
        userDao = UserDao(firebaseAuth)
        var ok = true
        with(binding){
            nextBtn.setOnClickListener {
                if(name.text?.isEmpty() == true){
                    nameLayout.error = "Fill up"
                    nameLayout.isErrorEnabled = true
                    ok = false
                }
                else{
                    nameLayout.isErrorEnabled = false
                    viewModel.name = name.text.toString()
                    ok = true
                }
                if(lastname.text?.isEmpty() == true){
                    lastnameLayout.error = "Fill up"
                    lastnameLayout.isErrorEnabled = true
                    ok = false

                }
                else{
                    lastnameLayout.isErrorEnabled = false
                    viewModel.lastname = lastname.text.toString()
                    ok = true
                }
                if(bio.text?.isEmpty() == true){
                    bioLayout.error = "Fill up"
                    bioLayout.isErrorEnabled = true
                    ok = false

                }
                else{
                    bioLayout.isErrorEnabled = false
                    viewModel.bio = bio.text.toString()
                    ok = true

                }
                if(ok){
                    val user = User(
                        viewModel.name,
                        viewModel.lastname,
                        viewModel.age,
                        viewModel.bio,
                        viewModel.height,
                        viewModel.weight,
                        viewModel.goalWeight,
                        viewModel.goal,
                        viewModel.level
                    )
                    userDao.saveData(user)
                    findNavController().navigate(
                        UserInformationFragmentDirections.actionUserInformationFragmentToHome()
                    )
                }
            }
        }
    }
}
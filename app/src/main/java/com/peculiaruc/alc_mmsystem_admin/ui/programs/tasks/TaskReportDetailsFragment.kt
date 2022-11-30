package com.peculiaruc.alc_mmsystem_admin.ui.programs.tasks

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.peculiaruc.alc_mmsystem_admin.R
import com.peculiaruc.alc_mmsystem_admin.databinding.FragmentTaskReportDetailsBinding
import com.peculiaruc.alc_mmsystem_admin.ui.base.BaseFragment

/**
 * Task report details fragment
 *
 * @constructor Create empty Task report details fragment
 */
class TaskReportDetailsFragment : BaseFragment<FragmentTaskReportDetailsBinding>() {
    override val layoutIdFragment: Int = R.layout.fragment_task_report_details
    override val viewModel = TaskReportsViewModel.getInstance()
    val TAG = "TaskReportDTag"

    private val TASK_REPORT_ID_ARGUMENT = "taskReportID"
    private var reportID = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle(false)
        arguments?.let {
            reportID = it.getInt(TASK_REPORT_ID_ARGUMENT)
        }
        binding.taskReportCloseButton.setOnClickListener {
            view.findNavController().popBackStack()
        }
        binding.taskReportShareButton.setOnClickListener {
            showShareDialog()
        }
        binding.taskReportDownloadButton.setOnClickListener {
            showDownloadDialog()
        }
    }


    private fun showDownloadDialog() {
        activity?.let {
            val dialog = Dialog(it)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_success)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val successTitle = dialog.findViewById<TextView>(R.id.success_dialog_title)
            successTitle.text = getString(R.string.report_download_dialog_success_message)
            val downloadImage = dialog.findViewById(R.id.success_image) as ImageView
            downloadImage.setImageResource(R.drawable.download_dialog_backgroun_1)
            val doneButton = dialog.findViewById(R.id.downloadDoneButton) as Button
            doneButton.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }


    private fun showShareDialog() {
        activity?.let {

            val dialog = Dialog(it)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.dialog_share)
            dialog.window?.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            val cancelButton = dialog.findViewById(R.id.cancelButton) as Button
            val emailButton = dialog.findViewById(R.id.shareEmailButton) as Button
            emailButton.setOnClickListener {
                sendEmail()
                dialog.dismiss()
            }
            cancelButton.setOnClickListener { dialog.dismiss() }
            dialog.show()
        }
    }

    /**
     * Send email
     *
     */
    fun sendEmail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_SUBJECT, "")
        }
        try {
            startActivity(Intent.createChooser(intent, null))
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, e.toString())
        }
    }
}
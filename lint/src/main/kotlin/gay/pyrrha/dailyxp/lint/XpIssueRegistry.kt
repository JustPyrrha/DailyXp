package gay.pyrrha.dailyxp.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.android.tools.lint.detector.api.Issue
import gay.pyrrha.dailyxp.lint.design.DesignSystemDetector

class XpIssueRegistry : IssueRegistry() {
    override val issues: List<Issue> = listOf(
        DesignSystemDetector.ISSUE,
        TestMethodNameDetector.FORMAT,
        TestMethodNameDetector.PREFIX
    )

    override val api: Int = CURRENT_API
    override val minApi: Int = 12
    override val vendor: Vendor = Vendor(
        vendorName = "DailyXp"
        //todo: add feedback url and contact before publishing source
    )
}

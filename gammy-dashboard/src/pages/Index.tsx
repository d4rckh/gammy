import AchievementsUnlockCount from "@/components/achievements/AchivementsUnlockCount";
import InteractionsCount from "@/components/interactions/IntereactionCount";
import PageTitle from "@/components/pageTitle";
import AnalyticsCard from "@/components/ui/custom/AnalyticsCard";
import {useGameAnalytics} from "@/lib/api/queries";

export default function IndexPage() {
    const analytics = useGameAnalytics();

    return (
        <>
            <PageTitle title="Dashboard"
                       description="Welcome to your dashboard, here you can find some quick insights."/>

            <div className="grid gap-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 mb-6">
                <AnalyticsCard
                    value={analytics.data?.totalPlayers ?? 0}
                    name="Total Players"
                />
                <AnalyticsCard
                    value={analytics.data?.totalAchievements ?? 0}
                    name="Total Achievements"
                />
                <AnalyticsCard
                    value={analytics.data?.totalReports ?? 0}
                    name="Total Reports"
                />
                <AnalyticsCard
                    value={Object.values(analytics.data?.interactionsCount ?? {}).reduce((a, b) => a + b, 0)}
                    name="Total Interactions"
                />
            </div>

            <div className="grid gap-6 md:grid-cols-2">
                <AchievementsUnlockCount/>
                <InteractionsCount/>
            </div>
        </>
    );
}

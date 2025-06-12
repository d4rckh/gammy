import AchievementsUnlockCount from "@/components/achievements/AchivementsUnlockCount";
import InteractionsCount from "@/components/interactions/IntereactionCount";
import PageTitle from "@/components/pageTitle";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { useGameAnalytics } from "@/lib/api/queries";

function AnalyticsCard({ name, value }: { name: string, value: number }) {
  return (
    <Card className="hover:shadow-lg transition-shadow duration-300 rounded-lg">
      <CardHeader>
        <CardTitle className="flex items-center gap-2 text-lg font-semibold text-primary-600">
          {name}
        </CardTitle>
      </CardHeader>
      <CardContent className="text-5xl">
        {value}
      </CardContent>
    </Card>
  );
}

export default function IndexPage() {
  const analytics = useGameAnalytics();

  return (
    <>
      <PageTitle title="Dashboard" description="Welcome to your dashboard, here you can find some quick insights." />

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
        <AchievementsUnlockCount />
        <InteractionsCount />
      </div>
    </>
  );
}

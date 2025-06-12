import { useGameAnalytics } from "@/lib/api/queries";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from "recharts";
import { ChartContainer, ChartTooltip, ChartTooltipContent } from "../ui/chart";

export default function AchievementsUnlockCount() {
  const { data: gameAnalytics, isLoading } = useGameAnalytics();

  if (isLoading || !gameAnalytics) {
    return <div className="p-4 text-muted-foreground">Loading analytics...</div>;
  }

  const { achievementsUnlockCount } = gameAnalytics;

  const achievementData = Object.entries(achievementsUnlockCount).map(([name, count]) => ({
    name,
    count,
  }));

  return (
    <Card>
      <CardHeader>
        <CardTitle>Achievements Unlock Count</CardTitle>
      </CardHeader>
      <CardContent>
        {achievementData.length === 0 ? (
          <p className="text-muted-foreground">No achievements unlocked yet.</p>
        ) : (
          <div className="h-[400px]">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={achievementData} margin={{ top: 16, right: 30, left: 0, bottom: 50 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" angle={-45} textAnchor="end" interval={0} />
                <YAxis />
                <Bar dataKey="count" fill="#4f46e5" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        )}
      </CardContent>
    </Card>
  );
}

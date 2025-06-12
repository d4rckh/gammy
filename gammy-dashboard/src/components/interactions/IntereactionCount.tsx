import { useGameAnalytics } from "@/lib/api/queries";
import {
  Card,
  CardContent,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from "recharts";

export default function InteractionsCount() {
  const { data: gameAnalytics, isLoading } = useGameAnalytics();

  if (isLoading || !gameAnalytics) {
    return <div className="p-4 text-muted-foreground">Loading analytics...</div>;
  }

  const { interactionsCount } = gameAnalytics;

  const interactionData = Object.entries(interactionsCount).map(([name, count]) => ({
    name,
    count,
  }));

  return (
    <div>
      <Card>
        <CardHeader>
          <CardTitle>Interactions Count</CardTitle>
        </CardHeader>
        <CardContent>
          {interactionData.length === 0 ? (
            <p className="text-muted-foreground">No interactions recorded yet.</p>
          ) : (
            <div className="h-[400px]">
              <ResponsiveContainer width="100%" height="100%">
                <BarChart data={interactionData} margin={{ top: 16, right: 30, left: 0, bottom: 50 }}>
                  <CartesianGrid strokeDasharray="3 3" />
                  <XAxis dataKey="name" angle={-45} textAnchor="end" interval={0} />
                  <YAxis />
                  <Bar dataKey="count" fill="#0ea5e9" radius={[4, 4, 0, 0]} />
                </BarChart>
              </ResponsiveContainer>
            </div>
          )}
        </CardContent>
      </Card>
    </div>
  );
}

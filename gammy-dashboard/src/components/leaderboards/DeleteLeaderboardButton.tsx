import { Button } from "@/components/ui/button";
import { useDeleteAchievementMutation, useDeleteLeaderboard } from "@/lib/api/mutations";
import type { components } from "@/lib/api/v1";

export default function DeleteLeaderboardButton({ lb }: { lb: components["schemas"]["GameAchievementEntity"] }) {
  const deleteOverride = useDeleteLeaderboard();

  return <Button
    onClick={() => {
      deleteOverride.mutate({
        body: lb
      })
    }}
    size={"sm"} variant={"destructive"}>
    Delete
  </Button>
}
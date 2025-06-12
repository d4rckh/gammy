import { Button } from "@/components/ui/button";
import { useUpdatePlayerMutation } from "@/lib/api/mutations";
import type { components } from "@/lib/api/v1";

export function BanPlayerButton({ player }: { player: components["schemas"]["PlayerEntity"] }) {
  const mutatePlayer = useUpdatePlayerMutation(player.id ?? -1);

  return <Button
    variant={"destructive"}
    size={"sm"}
  
    onClick={() => {
      mutatePlayer.mutate({
        body: {
          ...player,
          banned: !player.banned
        }
      })
    }}
  >
    {player.banned ? "Unban" : "Ban"} Player
  </Button>
}
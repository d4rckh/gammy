import { Button } from "@/components/ui/button";
import { useDeleteInteractionType } from "@/lib/api/mutations";
import type { components } from "@/lib/api/v1";

export default function DeleteInteractionTypeButton({ interaction }: { interaction: components["schemas"]["GameInteractionEntity"] }) {
  const deleteOverride = useDeleteInteractionType();

  return <Button
    onClick={() => {
      deleteOverride.mutate({
        body: interaction
      })
    }}
    size={"sm"} variant={"destructive"}>
    Delete
  </Button>
}
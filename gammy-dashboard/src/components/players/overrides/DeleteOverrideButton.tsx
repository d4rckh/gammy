import { Button } from "@/components/ui/button";
import { Dialog, DialogClose, DialogContent, DialogTitle, DialogTrigger } from "@/components/ui/dialog";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Select, SelectContent, SelectItem, SelectLabel, SelectTrigger, SelectValue } from "@/components/ui/select";
import { useCreatePlayerParameterOverrideMutation, useDeletePlayerParameterOverrideMutation } from "@/lib/api/mutations";
import { useParameters } from "@/lib/api/queries";
import type { components } from "@/lib/api/v1";

export default function DeleteOverrideButton({ override }: { override: components["schemas"]["PlayerParameterOverrideEntity"] }) {
  const deleteOverride = useDeletePlayerParameterOverrideMutation(override.player?.id ?? 0);

  return <Button
    onClick={() => {
      deleteOverride.mutate({
        body: override
      })
    }}
    size={"sm"} variant={"destructive"}>
    Delete
  </Button>
}
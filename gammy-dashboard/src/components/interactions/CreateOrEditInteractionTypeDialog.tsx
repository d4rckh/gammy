import type { components } from "@/lib/api/v1";
import {
  Dialog,
  DialogContent,
  DialogTitle,
  DialogTrigger,
} from "../ui/dialog";
import { Button } from "../ui/button";
import { useEffect, useState } from "react";
import { Input } from "../ui/input";
import { Label } from "../ui/label";
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from "../ui/select";
import { useCreateInteractionType, useUpdateInteractionType } from "@/lib/api/mutations";

type GameInteractionEntity = components["schemas"]["GameInteractionEntity"];
type GameInteractionStreak = components["schemas"]["GameInteractionStreak"];

export default function CreateOrEditGameInteractionDialog({
  interaction,
}: {
  interaction?: GameInteractionEntity;
}) {
  const create = useCreateInteractionType();
  const update = useUpdateInteractionType();

  const [open, setOpen] = useState(false);

  const [body, setBody] = useState<GameInteractionEntity>({
    apiName: "",
    displayName: "",
    streak: "NONE",
  });

  const [errors, setErrors] = useState<{ [key: string]: boolean }>({});

  useEffect(() => {
    if (interaction) {
      setBody(interaction);
    }
  }, [interaction]);

  const handleChange = (
    field: keyof Omit<GameInteractionEntity, "id">,
    value: string
  ) => {
    setBody((prev) => ({ ...prev, [field]: value }));
    setErrors((prev) => ({ ...prev, [field]: false }));
  };

  const validateAndSubmit = () => {
    const newErrors: typeof errors = {};

    if (!body.apiName?.trim()) newErrors.apiName = true;
    if (!body.displayName?.trim()) newErrors.displayName = true;
    if (!body.streak) newErrors.streak = true;

    setErrors(newErrors);
    if (Object.keys(newErrors).length > 0) return;

    const action = interaction
      ? update.mutateAsync({ body })
      : create.mutateAsync({ body });

    action.then(() => setOpen(false));
  };

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogTrigger asChild>
        <Button size="sm">{interaction ? "Edit" : "New Interaction"}</Button>
      </DialogTrigger>
      <DialogContent>
        <DialogTitle>{interaction ? "Edit Interaction" : "New Interaction"}</DialogTitle>

        <Label>API Name</Label>
        <Input
          placeholder="API Name"
          value={body.apiName ?? ""}
          className={errors.apiName ? "border-red-500" : ""}
          onChange={(e) => handleChange("apiName", e.target.value)}
        />

        <Label>Display Name</Label>
        <Input
          placeholder="Display Name"
          value={body.displayName ?? ""}
          className={errors.displayName ? "border-red-500" : ""}
          onChange={(e) => handleChange("displayName", e.target.value)}
        />

        <Label>Streak</Label>
        <Select
          value={body.streak ?? "NONE"}
          onValueChange={(val) =>
            handleChange("streak", val as GameInteractionStreak)
          }
        >
          <SelectTrigger className="w-full">
            <SelectValue placeholder="Select streak type" />
          </SelectTrigger>
          <SelectContent>
            <SelectItem value="NONE">NONE</SelectItem>
            <SelectItem value="DAILY">DAILY</SelectItem>
          </SelectContent>
        </Select>

        <Button onClick={validateAndSubmit}>
          {interaction ? "Save" : "Create"}
        </Button>
      </DialogContent>
    </Dialog>
  );
}
